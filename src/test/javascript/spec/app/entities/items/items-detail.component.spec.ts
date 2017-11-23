/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuditorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ItemsDetailComponent } from '../../../../../../main/webapp/app/entities/items/items-detail.component';
import { ItemsService } from '../../../../../../main/webapp/app/entities/items/items.service';
import { Items } from '../../../../../../main/webapp/app/entities/items/items.model';

describe('Component Tests', () => {

    describe('Items Management Detail Component', () => {
        let comp: ItemsDetailComponent;
        let fixture: ComponentFixture<ItemsDetailComponent>;
        let service: ItemsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuditorTestModule],
                declarations: [ItemsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ItemsService,
                    JhiEventManager
                ]
            }).overrideTemplate(ItemsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ItemsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ItemsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Items('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.items).toEqual(jasmine.objectContaining({id: 'aaa'}));
            });
        });
    });

});
