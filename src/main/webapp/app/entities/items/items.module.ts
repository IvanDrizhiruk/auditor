import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuditorSharedModule } from '../../shared';
import {
    ItemsService,
    ItemsPopupService,
    ItemsComponent,
    ItemsDetailComponent,
    ItemsDialogComponent,
    ItemsPopupComponent,
    ItemsDeletePopupComponent,
    ItemsDeleteDialogComponent,
    itemsRoute,
    itemsPopupRoute,
    ItemsResolvePagingParams,
    CustomParams,
} from './';

const ENTITY_STATES = [
    ...itemsRoute,
    ...itemsPopupRoute,
];

@NgModule({
    imports: [
        AuditorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ItemsComponent,
        ItemsDetailComponent,
        ItemsDialogComponent,
        ItemsDeleteDialogComponent,
        ItemsPopupComponent,
        ItemsDeletePopupComponent,
    ],
    entryComponents: [
        ItemsComponent,
        ItemsDialogComponent,
        ItemsPopupComponent,
        ItemsDeleteDialogComponent,
        ItemsDeletePopupComponent,
    ],
    providers: [
        ItemsService,
        ItemsPopupService,
        ItemsResolvePagingParams,
        CustomParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuditorItemsModule {}
