(function () {
    'use strict';

    angular
        .module('auditorApp')
        .controller('FindController', FindController);

    FindController.$inject = ['$scope', 'FindService'];

    function FindController($scope, FindService) {
        var vm = this;

        vm.query;
        vm.etities;
        vm.count;
        vm.focusOnFindInput = true;
        vm.focusOnCountInput = false;

        vm.onFind = onFind;
        vm.onCountEnter = onCountEnter;

        function onFind() {
            console.log("ISD on find", vm.query);
            vm.etities = [{name: 'test'}]

            FindService.findEntities(vm.query).then(
                function(entities) {
                    vm.etities = entities;
                    if (vm.etities.length > 0) {
                        vm.focusOnCountInput = true;
                    }
                }
            );
        }

        function onCountEnter() {
            FindService.registryCount(vm.etities[0], parseInt(vm.count));

            vm.query = '';
            vm.etities = [];
            vm.count = '';
            vm.focusOnFindInput = true;
        }
    }
})();
