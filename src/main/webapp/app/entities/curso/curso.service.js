(function() {
    'use strict';
    angular
        .module('unpsipApp')
        .factory('Curso', Curso);

    Curso.$inject = ['$resource'];

    function Curso ($resource) {
        var resourceUrl =  'api/cursos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
