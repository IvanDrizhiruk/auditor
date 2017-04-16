(function() {
    'use strict';

    angular
        .module('auditorApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('find', {
            parent: 'app',
            url: '/find',
            data: {
                authorities: [],
                pageTitle: 'global.menu.find'
            },
            views: {
                'content@': {
                    templateUrl: 'app/find/find.html',
                    controller: 'FindController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
