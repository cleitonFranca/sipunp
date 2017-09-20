(function() {
    'use strict';

    angular
        .module('unpsipApp')
        .factory('ProfessorSearch', ProfessorSearch);

    ProfessorSearch.$inject = ['$resource'];

    function ProfessorSearch($resource) {
        var resourceUrl =  'api/_search/professors/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
