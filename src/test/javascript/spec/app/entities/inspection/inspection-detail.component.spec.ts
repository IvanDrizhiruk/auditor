/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuditorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { InspectionDetailComponent } from '../../../../../../main/webapp/app/entities/inspection/inspection-detail.component';
import { InspectionService } from '../../../../../../main/webapp/app/entities/inspection/inspection.service';
import { Inspection } from '../../../../../../main/webapp/app/entities/inspection/inspection.model';

describe('Component Tests', () => {

    describe('Inspection Management Detail Component', () => {
        let comp: InspectionDetailComponent;
        let fixture: ComponentFixture<InspectionDetailComponent>;
        let service: InspectionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuditorTestModule],
                declarations: [InspectionDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    InspectionService,
                    JhiEventManager
                ]
            }).overrideTemplate(InspectionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InspectionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InspectionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Inspection('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.inspection).toEqual(jasmine.objectContaining({id: 'aaa'}));
            });
        });
    });

});
