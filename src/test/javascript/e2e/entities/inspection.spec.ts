import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Inspection e2e test', () => {

    let navBarPage: NavBarPage;
    let inspectionDialogPage: InspectionDialogPage;
    let inspectionComponentsPage: InspectionComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Inspections', () => {
        navBarPage.goToEntity('inspection');
        inspectionComponentsPage = new InspectionComponentsPage();
        expect(inspectionComponentsPage.getTitle()).toMatch(/auditorApp.inspection.home.title/);

    });

    it('should load create Inspection dialog', () => {
        inspectionComponentsPage.clickOnCreateButton();
        inspectionDialogPage = new InspectionDialogPage();
        expect(inspectionDialogPage.getModalTitle()).toMatch(/auditorApp.inspection.home.createOrEditLabel/);
        inspectionDialogPage.close();
    });

    it('should create and save Inspections', () => {
        inspectionComponentsPage.clickOnCreateButton();
        inspectionDialogPage.setNameInput('name');
        expect(inspectionDialogPage.getNameInput()).toMatch('name');
        inspectionDialogPage.setDescriptionInput('description');
        expect(inspectionDialogPage.getDescriptionInput()).toMatch('description');
        inspectionDialogPage.setStartDateInput('2000-12-31');
        expect(inspectionDialogPage.getStartDateInput()).toMatch('2000-12-31');
        inspectionDialogPage.setEndDateInput('2000-12-31');
        expect(inspectionDialogPage.getEndDateInput()).toMatch('2000-12-31');
        inspectionDialogPage.setItemsInput('5');
        expect(inspectionDialogPage.getItemsInput()).toMatch('5');
        inspectionDialogPage.save();
        expect(inspectionDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class InspectionComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-inspection div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class InspectionDialogPage {
    modalTitle = element(by.css('h4#myInspectionLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    descriptionInput = element(by.css('input#field_description'));
    startDateInput = element(by.css('input#field_startDate'));
    endDateInput = element(by.css('input#field_endDate'));
    itemsInput = element(by.css('input#field_items'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function (name) {
        this.nameInput.sendKeys(name);
    }

    getNameInput = function () {
        return this.nameInput.getAttribute('value');
    }

    setDescriptionInput = function (description) {
        this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput = function () {
        return this.descriptionInput.getAttribute('value');
    }

    setStartDateInput = function (startDate) {
        this.startDateInput.sendKeys(startDate);
    }

    getStartDateInput = function () {
        return this.startDateInput.getAttribute('value');
    }

    setEndDateInput = function (endDate) {
        this.endDateInput.sendKeys(endDate);
    }

    getEndDateInput = function () {
        return this.endDateInput.getAttribute('value');
    }

    setItemsInput = function (items) {
        this.itemsInput.sendKeys(items);
    }

    getItemsInput = function () {
        return this.itemsInput.getAttribute('value');
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
