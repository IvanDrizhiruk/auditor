import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Inspection } from './inspection.model';
import { InspectionService } from './inspection.service';

@Injectable()
export class InspectionPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private inspectionService: InspectionService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.inspectionService.find(id).subscribe((inspection) => {
                    if (inspection.startDate) {
                        inspection.startDate = {
                            year: inspection.startDate.getFullYear(),
                            month: inspection.startDate.getMonth() + 1,
                            day: inspection.startDate.getDate()
                        };
                    }
                    if (inspection.endDate) {
                        inspection.endDate = {
                            year: inspection.endDate.getFullYear(),
                            month: inspection.endDate.getMonth() + 1,
                            day: inspection.endDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.inspectionModalRef(component, inspection);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.inspectionModalRef(component, new Inspection());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    inspectionModalRef(component: Component, inspection: Inspection): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.inspection = inspection;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
