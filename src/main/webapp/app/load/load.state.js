(function() {
    'use strict';

    angular
        .module('auditorApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('load', {
            parent: 'app',
            url: '/load',
            data: {
                authorities: [],
                pageTitle: 'global.menu.load'
            },
            views: {
                'content@': {
                    templateUrl: 'app/load/load.html',
                    controller: 'LoadController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
