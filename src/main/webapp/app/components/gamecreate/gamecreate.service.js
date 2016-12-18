(function() {
    'use strict';

    angular
        .module('stuffApp')
        .factory('GameCreateService', GameCreateService);

    GameCreateService.$inject = ['$uibModal'];

    function GameCreateService ($uibModal) {
        var service = {
            open: open
        };

        var modalInstance = null;
        var resetModal = function () {
            modalInstance = null;
        };

        return service;

        function open () {
            if (modalInstance !== null) return;
            modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'app/components/gamecreate/gamecreate.html',
                controller: 'GameCreateController',
                controllerAs: 'vm'
            });
            modalInstance.result.then(
                resetModal,
                resetModal
            );
        }
    }
})();
