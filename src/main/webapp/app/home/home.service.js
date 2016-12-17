(function() {
    'use strict';

    angular
        .module('stuffApp')
        .factory('GameService', GameService);

    GameService.$inject = ['$resource'];

    function GameService ($resource) {
        var service = $resource('api/games', {}, {
            'findAll': { method: 'GET', isArray: true}
        });

        return service;
    }
})();
