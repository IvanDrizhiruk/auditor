import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Items e2e test', () => {

    let navBarPage: NavBarPage;
    let itemsDialogPage: ItemsDialogPage;
    let itemsComponentsPage: ItemsComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Items', () => {
        navBarPage.goToEntity('items');
        itemsComponentsPage = new ItemsComponentsPage();
        expect(itemsComponentsPage.getTitle()).toMatch(/auditorApp.items.home.title/);

    });

    it('should load create Items dialog', () => {
        itemsComponentsPage.clickOnCreateButton();
        itemsDialogPage = new ItemsDialogPage();
        expect(itemsDialogPage.getModalTitle()).toMatch(/auditorApp.items.home.createOrEditLabel/);
        itemsDialogPage.close();
    });

    it('should create and save Items', () => {
        itemsComponentsPage.clickOnCreateButton();
        itemsDialogPage.setInspectionIdInput('inspectionId');
        expect(itemsDialogPage.getInspectionIdInput()).toMatch('inspectionId');
        itemsDialogPage.save();
        expect(itemsDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ItemsComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-items div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ItemsDialogPage {
    modalTitle = element(by.css('h4#myItemsLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    inspectionIdInput = element(by.css('input#field_inspectionId'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setInspectionIdInput = function (inspectionId) {
        this.inspectionIdInput.sendKeys(inspectionId);
    }

    getInspectionIdInput = function () {
        return this.inspectionIdInput.getAttribute('value');
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
