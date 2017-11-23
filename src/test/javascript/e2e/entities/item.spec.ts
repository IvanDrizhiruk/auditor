import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Item e2e test', () => {

    let navBarPage: NavBarPage;
    let itemDialogPage: ItemDialogPage;
    let itemComponentsPage: ItemComponentsPage;
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
        navBarPage.goToEntity('item');
        itemComponentsPage = new ItemComponentsPage();
        expect(itemComponentsPage.getTitle()).toMatch(/auditorApp.item.home.title/);

    });

    it('should load create Item dialog', () => {
        itemComponentsPage.clickOnCreateButton();
        itemDialogPage = new ItemDialogPage();
        expect(itemDialogPage.getModalTitle()).toMatch(/auditorApp.item.home.createOrEditLabel/);
        itemDialogPage.close();
    });

    it('should create and save Items', () => {
        itemComponentsPage.clickOnCreateButton();
        itemDialogPage.setCodeInput('code');
        expect(itemDialogPage.getCodeInput()).toMatch('code');
        itemDialogPage.setDescriptionInput('description');
        expect(itemDialogPage.getDescriptionInput()).toMatch('description');
        itemDialogPage.setExpectedCountInput('5');
        expect(itemDialogPage.getExpectedCountInput()).toMatch('5');
        itemDialogPage.setActualCountInput('5');
        expect(itemDialogPage.getActualCountInput()).toMatch('5');
        itemDialogPage.save();
        expect(itemDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ItemComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-item div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ItemDialogPage {
    modalTitle = element(by.css('h4#myItemLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    codeInput = element(by.css('input#field_code'));
    descriptionInput = element(by.css('input#field_description'));
    expectedCountInput = element(by.css('input#field_expectedCount'));
    actualCountInput = element(by.css('input#field_actualCount'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setCodeInput = function (code) {
        this.codeInput.sendKeys(code);
    }

    getCodeInput = function () {
        return this.codeInput.getAttribute('value');
    }

    setDescriptionInput = function (description) {
        this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput = function () {
        return this.descriptionInput.getAttribute('value');
    }

    setExpectedCountInput = function (expectedCount) {
        this.expectedCountInput.sendKeys(expectedCount);
    }

    getExpectedCountInput = function () {
        return this.expectedCountInput.getAttribute('value');
    }

    setActualCountInput = function (actualCount) {
        this.actualCountInput.sendKeys(actualCount);
    }

    getActualCountInput = function () {
        return this.actualCountInput.getAttribute('value');
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
