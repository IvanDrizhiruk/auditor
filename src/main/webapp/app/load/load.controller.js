(function () {
    'use strict';

    angular
        .module('auditorApp')
        .controller('LoadController', LoadController);

    LoadController.$inject = ['$scope', 'Employee'];

    function LoadController($scope, Employee) {

        console.log("INITED");

        var vm = this;

        vm.onRead = onRead;
        vm.onError = onError;


        function onRead(workbook) {
            console.log("ISD workbook", workbook);

            //let cells = workbook.Sheets.TDSheet;
            let sheet_name_list = workbook.SheetNames;
            let cells = workbook.Sheets[sheet_name_list[0]];

            for (let i = 1; cells['A' + i]; i++) {

                console.log("ISD A" + i, cells['A' + i].v);
                console.log("ISD C" + i, cells['C' + i].v);
                console.log("ISD D" + i, cells['D' + i].v);
                console.log("ISD E" + i, cells['E' + i].v);


                let employee = {
                    articul : cells['A' + i].v,
                    label: cells['C' + i].v,
                    barcode: cells['D' + i].v,
                    expectedNumber: cells['E' + i].v,
                    auditedNumber : 0
                };


                console.log("ISD try to insert", employee);

                Employee.save(
                    employee,
                    res => console.log('ISD added', res),
                    error => console.log('ISD error', error));
            }
        }

        function onError(error) {
            console.log("ISD error", error);
        }

    }
})();
