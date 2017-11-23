import { BaseEntity } from './../../shared';

export class Items implements BaseEntity {
    constructor(
        public id?: string,
        public inspectionId?: string,
    ) {
    }
}
