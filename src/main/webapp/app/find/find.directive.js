(function () {
    'use strict';

    angular
        .module('auditorApp')
        .directive('faqToday', faqToday);

    faqToday.$inject = ['FindService'];

    function faqToday(FindService) {

        var directive = {
            restrict: 'A',
            template: 'Today is {{weekday}}, the {{dateWithOrdinal}} of {{month}}, {{fullYear}}',
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs) {

            element.addClass('text-left');

            var today = new Date();
            var date = today.getDate();

            scope.weekday = today.toLocaleString('en-us', {weekday: 'long'});
            scope.dateWithOrdinal = date + FindService.getOrdinalIndicator(date);
            scope.month = today.toLocaleString('en-us', {month: 'long'});
            scope.fullYear = today.getFullYear();
        }
    }
})();
