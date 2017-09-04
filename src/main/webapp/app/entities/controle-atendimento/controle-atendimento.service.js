(function() {
    'use strict';
    angular
        .module('unpsipApp')
        .factory('ControleAtendimento', ControleAtendimento);

    ControleAtendimento.$inject = ['$resource', 'DateUtils'];

    function ControleAtendimento ($resource, DateUtils) {
        var resourceUrl =  'api/controle-atendimentos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.idade = DateUtils.convertLocalDateFromServer(data.idade);
                        data.dataCadastro = DateUtils.convertDateTimeFromServer(data.dataCadastro);
                        data.dataAlteracao = DateUtils.convertLocalDateFromServer(data.dataAlteracao);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.idade = DateUtils.convertLocalDateToServer(copy.idade);
                    copy.dataAlteracao = DateUtils.convertLocalDateToServer(copy.dataAlteracao);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.idade = DateUtils.convertLocalDateToServer(copy.idade);
                    copy.dataAlteracao = DateUtils.convertLocalDateToServer(copy.dataAlteracao);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
