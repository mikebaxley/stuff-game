(function() {
    'use strict';

    angular
        .module('stuffApp')
        .controller('GameCreateController', GameCreateController);

    GameCreateController.$inject = ['$rootScope', '$state', '$timeout', 'Auth', '$uibModalInstance'];

    function GameCreateController ($rootScope, $state, $timeout, Auth, $uibModalInstance) {
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
            /*
            Auth.login({
                username: vm.username,
                password: vm.password,
                rememberMe: vm.rememberMe
            }).then(function () {
                vm.authenticationError = false;
                $uibModalInstance.close();
                if ($state.current.name === 'register' || $state.current.name === 'activate' ||
                    $state.current.name === 'finishReset' || $state.current.name === 'requestReset') {
                    $state.go('home');
                }

                $rootScope.$broadcast('authenticationSuccess');

                // previousState was set in the authExpiredInterceptor before being redirected to login modal.
                // since login is successful, go to stored previousState and clear previousState
                if (Auth.getPreviousState()) {
                    var previousState = Auth.getPreviousState();
                    Auth.resetPreviousState();
                    $state.go(previousState.name, previousState.params);
                }
            }).catch(function () {
                vm.authenticationError = true;
            });
            */
        }
    }
})();
