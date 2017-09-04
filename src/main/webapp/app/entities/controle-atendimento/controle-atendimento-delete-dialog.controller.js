(function() {
    'use strict';

    angular
        .module('unpsipApp')
        .controller('ControleAtendimentoDeleteController',ControleAtendimentoDeleteController);

    ControleAtendimentoDeleteController.$inject = ['$uibModalInstance', 'entity', 'ControleAtendimento'];

    function ControleAtendimentoDeleteController($uibModalInstance, entity, ControleAtendimento) {
        var vm = this;

        vm.controleAtendimento = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ControleAtendimento.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
