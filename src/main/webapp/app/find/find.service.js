(function () {
    'use strict';

    angular
        .module('auditorApp')
        .factory('FindService', FindService);

    FindService.$inject = ['$resource', 'Employee'];

    function FindService($resource, Employee) {
        var service = {
            findEntities: findEntities,
            registryCount: registryCount
        };

        return service;

        function findEntities(query) {
            return $resource('api/employees/find/:query', {}, {
                'get': {
                    method: 'GET',
                    isArray: true,
                    transformResponse: function (data) {
                        if (data) {
                            data = angular.fromJson(data);
                        }
                        return data;
                    }
                }
            }).get({query : query}).$promise;
        }

        function registryCount(oldEntity, count) {
            let newEntity = angular.copy(oldEntity);

            newEntity.auditedNumber += count;

            return Employee.update(newEntity);
        }
    }
})();
