(function() {
    'use strict';

    angular
        .module('stuffApp')
        .controller('GameCreateController', GameCreateController);

    GameCreateController.$inject = ['$http', '$rootScope', '$state', '$timeout',  '$uibModalInstance'];

    function GameCreateController ($http, $rootScope, $state, $timeout,  $uibModalInstance) {
        var vm = this;

        vm.invalidName = false;
        vm.cancel = cancel;
        vm.gameName = null;
        vm.createGame = createGame;
        
        $timeout(function (){angular.element('#gamename').focus();});

        function cancel () {
            $uibModalInstance.dismiss('cancel');
        }

        function createGame (event) {
        	if(/^\s*\S+.*/.test(vm.gameName) === false){
                vm.invalidName = true;
                return;
        	}else{
                vm.invalidName = false;
        	}
        	const gameName = vm.gameName.trim();
            console.log('Creating game with name: ', gameName);
            event.preventDefault();

            var data = {
                gameName: gameName
            };
  
            $http.post('api/games', data).success(function (data, status, headers)  {
                $uibModalInstance.close();
            });
        }
    }
})();
