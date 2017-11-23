import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Items } from './items.model';
import { ItemsPopupService } from './items-popup.service';
import { ItemsService } from './items.service';

@Component({
    selector: 'jhi-items-dialog',
    templateUrl: './items-dialog.component.html'
})
export class ItemsDialogComponent implements OnInit {

    items: Items;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private itemsService: ItemsService,
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
        if (this.items.id !== undefined) {
            this.subscribeToSaveResponse(
                this.itemsService.update(this.items));
        } else {
            this.subscribeToSaveResponse(
                this.itemsService.create(this.items));
        }
    }

    private subscribeToSaveResponse(result: Observable<Items>) {
        result.subscribe((res: Items) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Items) {
        this.eventManager.broadcast({ name: 'itemsListModification', content: 'OK'});
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
    selector: 'jhi-items-popup',
    template: ''
})
export class ItemsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private itemsPopupService: ItemsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.itemsPopupService
                    .open(ItemsDialogComponent as Component, params['id']);
            } else {
                this.itemsPopupService
                    .open(ItemsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
