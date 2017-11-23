import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AuditorInspectionModule } from './inspection/inspection.module';
import { AuditorItemModule } from './item/item.module';
import { AuditorItemsModule } from './items/items.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        AuditorInspectionModule,
        AuditorItemModule,
        AuditorItemsModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuditorEntityModule {}
