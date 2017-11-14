import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { InspectionComponent } from './inspection.component';
import { InspectionDetailComponent } from './inspection-detail.component';
import { InspectionPopupComponent } from './inspection-dialog.component';
import { InspectionDeletePopupComponent } from './inspection-delete-dialog.component';

@Injectable()
export class InspectionResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const inspectionRoute: Routes = [
    {
        path: 'inspection',
        component: InspectionComponent,
        resolve: {
            'pagingParams': InspectionResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auditorApp.inspection.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'inspection/:id',
        component: InspectionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auditorApp.inspection.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const inspectionPopupRoute: Routes = [
    {
        path: 'inspection-new',
        component: InspectionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auditorApp.inspection.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'inspection/:id/edit',
        component: InspectionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auditorApp.inspection.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'inspection/:id/delete',
        component: InspectionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auditorApp.inspection.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
