(function() {
    'use strict';

    angular
        .module('unpsipApp')
        .factory('ControleAtendimentoSearch', ControleAtendimentoSearch);

    ControleAtendimentoSearch.$inject = ['$resource'];

    function ControleAtendimentoSearch($resource) {
        var resourceUrl =  'api/_search/controle-atendimentos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
