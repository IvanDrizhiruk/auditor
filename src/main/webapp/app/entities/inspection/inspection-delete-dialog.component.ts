import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Inspection } from './inspection.model';
import { InspectionPopupService } from './inspection-popup.service';
import { InspectionService } from './inspection.service';

@Component({
    selector: 'jhi-inspection-delete-dialog',
    templateUrl: './inspection-delete-dialog.component.html'
})
export class InspectionDeleteDialogComponent {

    inspection: Inspection;

    constructor(
        private inspectionService: InspectionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.inspectionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'inspectionListModification',
                content: 'Deleted an inspection'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-inspection-delete-popup',
    template: ''
})
export class InspectionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inspectionPopupService: InspectionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.inspectionPopupService
                .open(InspectionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
