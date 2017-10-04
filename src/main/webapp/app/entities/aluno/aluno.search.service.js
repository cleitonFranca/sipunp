(function() {
    'use strict';

    angular
        .module('unpsipApp')
        .factory('AlunoSearch', AlunoSearch);

    AlunoSearch.$inject = ['$resource'];

    function AlunoSearch($resource) {
        var resourceUrl =  'api/_search/alunos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
