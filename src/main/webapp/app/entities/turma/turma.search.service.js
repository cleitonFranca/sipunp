(function() {
    'use strict';

    angular
        .module('unpsipApp')
        .factory('TurmaSearch', TurmaSearch);

    TurmaSearch.$inject = ['$resource'];

    function TurmaSearch($resource) {
        var resourceUrl =  'api/_search/turmas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
