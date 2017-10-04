(function() {
    'use strict';

    angular
        .module('unpsipApp')
        .factory('CursoSearch', CursoSearch);

    CursoSearch.$inject = ['$resource'];

    function CursoSearch($resource) {
        var resourceUrl =  'api/_search/cursos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
