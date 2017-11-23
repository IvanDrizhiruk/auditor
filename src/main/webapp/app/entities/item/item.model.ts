import { BaseEntity } from './../../shared';

export class Item implements BaseEntity {
    constructor(
        public id?: string,
        public code?: string,
        public description?: string,
        public expectedCount?: number,
        public actualCount?: number,
    ) {
    }
}
