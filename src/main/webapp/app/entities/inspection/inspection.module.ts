import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuditorSharedModule } from '../../shared';
import {
    InspectionService,
    InspectionPopupService,
    InspectionComponent,
    InspectionDetailComponent,
    InspectionDialogComponent,
    InspectionPopupComponent,
    InspectionDeletePopupComponent,
    InspectionDeleteDialogComponent,
    inspectionRoute,
    inspectionPopupRoute,
    InspectionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...inspectionRoute,
    ...inspectionPopupRoute,
];

@NgModule({
    imports: [
        AuditorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        InspectionComponent,
        InspectionDetailComponent,
        InspectionDialogComponent,
        InspectionDeleteDialogComponent,
        InspectionPopupComponent,
        InspectionDeletePopupComponent,
    ],
    entryComponents: [
        InspectionComponent,
        InspectionDialogComponent,
        InspectionPopupComponent,
        InspectionDeleteDialogComponent,
        InspectionDeletePopupComponent,
    ],
    providers: [
        InspectionService,
        InspectionPopupService,
        InspectionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuditorInspectionModule {}
