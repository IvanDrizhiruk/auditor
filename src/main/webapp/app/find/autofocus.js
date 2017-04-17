(function() {
    'use strict';

    angular.module('auditorApp')
        .directive('autofocus', ['$timeout', function($timeout) {
            return {
                restrict: 'A',
                link : function($scope, $element) {
                    $timeout(function() {
                        $element[0].focus();
                    });
                }
            }
        }]);


    angular.module('auditorApp')

        .directive('focusMe', ['$timeout', '$parse', function ($timeout, $parse) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {

                var model = $parse(attrs.focusMe);
                scope.$watch(model, function (value) {
                    console.log('ISD value=', value);
                    if (value === true) {
                        $timeout(function () {
                            element[0].focus();
                        });
                    }
                });
                // to address @blesh's comment, set attribute value to 'false'
                // on blur event:
                element.bind('blur', function () {
                    console.log('blur');
                    scope.$apply(model.assign(scope, false));
                });
            }
        };
    }]);

})();
