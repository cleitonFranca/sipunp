(function() {
    'use strict';

    angular
        .module('unpsipApp')
        .controller('ControleAtendimentoDialogController', ControleAtendimentoDialogController);

    ControleAtendimentoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ControleAtendimento', 'Cliente'];

    function ControleAtendimentoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ControleAtendimento, Cliente) {
        var vm = this;

        vm.controleAtendimento = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.clientes = Cliente.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.controleAtendimento.id !== null) {
                ControleAtendimento.update(vm.controleAtendimento, onSaveSuccess, onSaveError);
            } else {
                ControleAtendimento.save(vm.controleAtendimento, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('unpsipApp:controleAtendimentoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.idade = false;
        vm.datePickerOpenStatus.dataCadastro = false;
        vm.datePickerOpenStatus.dataAlteracao = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
