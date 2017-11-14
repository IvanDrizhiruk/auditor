import { BaseEntity } from './../../shared';

export class Inspection implements BaseEntity {
    constructor(
        public id?: string,
        public name?: string,
        public description?: string,
        public companyId?: string,
        public startDate?: any,
        public endDate?: any,
    ) {
    }
}