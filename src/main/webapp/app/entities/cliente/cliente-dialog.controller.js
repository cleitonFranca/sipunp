(function() {
    'use strict';

    angular
        .module('unpsipApp')
        .controller('ClienteDialogController', ClienteDialogController);

    ClienteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Cliente', 'Endereco'];

    function ClienteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Cliente, Endereco) {
        var vm = this;

        vm.cliente = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.enderecos = Endereco.query({filter: 'cliente-is-null'});
        $q.all([vm.cliente.$promise, vm.enderecos.$promise]).then(function() {
            if (!vm.cliente.endereco || !vm.cliente.endereco.id) {
                return $q.reject();
            }
            return Endereco.get({id : vm.cliente.endereco.id}).$promise;
        }).then(function(endereco) {
            vm.enderecos.push(endereco);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cliente.id !== null) {
                Cliente.update(vm.cliente, onSaveSuccess, onSaveError);
            } else {
                Cliente.save(vm.cliente, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('unpsipApp:clienteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.nascimento = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
