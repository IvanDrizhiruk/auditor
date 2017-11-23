import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ItemsComponent } from './items.component';
import { ItemsDetailComponent } from './items-detail.component';
import { ItemsPopupComponent } from './items-dialog.component';
import { ItemsDeletePopupComponent } from './items-delete-dialog.component';

@Injectable()
export class ItemsResolvePagingParams implements Resolve<any> {

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

export const itemsRoute: Routes = [
    {
        path: 'items',
        component: ItemsComponent,
        resolve: {
            'pagingParams': ItemsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auditorApp.items.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'items/:id',
        component: ItemsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auditorApp.items.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const itemsPopupRoute: Routes = [
    {
        path: 'items-new',
        component: ItemsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auditorApp.items.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'items/:id/edit',
        component: ItemsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auditorApp.items.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'items/:id/delete',
        component: ItemsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auditorApp.items.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
