import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Inspection } from './inspection.model';
import { InspectionPopupService } from './inspection-popup.service';
import { InspectionService } from './inspection.service';

@Component({
    selector: 'jhi-inspection-dialog',
    templateUrl: './inspection-dialog.component.html'
})
export class InspectionDialogComponent implements OnInit {

    inspection: Inspection;
    isSaving: boolean;
    startDateDp: any;
    endDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private inspectionService: InspectionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.inspection.id !== undefined) {
            this.subscribeToSaveResponse(
                this.inspectionService.update(this.inspection));
        } else {
            this.subscribeToSaveResponse(
                this.inspectionService.create(this.inspection));
        }
    }

    private subscribeToSaveResponse(result: Observable<Inspection>) {
        result.subscribe((res: Inspection) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Inspection) {
        this.eventManager.broadcast({ name: 'inspectionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-inspection-popup',
    template: ''
})
export class InspectionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inspectionPopupService: InspectionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.inspectionPopupService
                    .open(InspectionDialogComponent as Component, params['id']);
            } else {
                this.inspectionPopupService
                    .open(InspectionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
