(function () {
    'use strict';

    angular
        .module('auditorApp')
        .controller('LoadController', LoadController);

    LoadController.$inject = ['$scope', 'LoadService'];

    function LoadController($scope, LoadService) {
        var vm = this;

        $scope.controllerMessage = todayToString();

        vm.todayToString = todayToString;

        function todayToString() {
            var today = new Date();
            var dayName = today.toLocaleString('en-us', {weekday: 'long'});
            var monthName = today.toLocaleString('en-us', {month: 'long'});
            var date = today.getDate();

            return 'Today is ' + dayName + ', the ' + dateWithOrdinal(date) + ' of ' + monthName + ', ' + today.getFullYear();
        }

        function dateWithOrdinal(date) {
            return date + LoadService.getOrdinalIndicator(date);
        }

    }
})();
