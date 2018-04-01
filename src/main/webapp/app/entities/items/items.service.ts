import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Items } from './items.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ItemsService {

    private resourceUrl = SERVER_API_URL + 'api/items';

    constructor(private http: Http) { }

    create(items: Items): Observable<Items> {
        const copy = this.convert(items);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(items: Items): Observable<Items> {
        const copy = this.convert(items);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<Items> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get('/api/inspections/' + req.inspectionId + '/items', options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: string): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Items.
     */
    private convertItemFromServer(json: any): Items {
        const entity: Items = Object.assign(new Items(), json);
        return entity;
    }

    /**
     * Convert a Items to a JSON which can be sent to the server.
     */
    private convert(items: Items): Items {
        const copy: Items = Object.assign({}, items);
        return copy;
    }
}
