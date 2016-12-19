(function() {
    'use strict';

    angular
        .module('stuffApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', 'GameService', 'GameCreateService', 'GameListService', '$state'];

    function HomeController ($scope, Principal, LoginService, GameService, GameCreateService, GameListService, $state) {
        var vm = this;
        
        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        vm.games = null;
        vm.createGameDialog = GameCreateService.open;
        
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        GameListService.connect();
        GameListService.subscribe();
        GameListService.receive().then(null, null, function(gameList) {
        	console.log('Got game list: ', gameList);
        	vm.games = gameList;
        });
        
        getAccount();

        
        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
        function createGame() {
            GameCreateService.open();
            GameListService.update();
        }
    }
})();
