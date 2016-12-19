(function() {
    'use strict';
    /* globals SockJS, Stomp */

    angular
        .module('stuffApp')
        .factory('GameListService', GameListService);

    GameListService.$inject = ['$rootScope', '$window', '$cookies', '$http', '$q', 'AuthServerProvider'];

    function GameListService ($rootScope, $window, $cookies, $http, $q, AuthServerProvider) {
        var stompClient = null;
        var subscriber = null;
        var listener = $q.defer();
        var connected = $q.defer();
        var alreadyConnectedOnce = false;

        var service = {
            connect: connect,
            disconnect: disconnect,
            receive: receive,
            update: update,
            subscribe: subscribe,
            unsubscribe: unsubscribe
        };

        return service;        
        
        function connect () {
        	console.log('Connecting!');
            //building absolute path so that websocket doesnt fail when deploying with a context path
            var loc = $window.location;
            var url = '//' + loc.host + loc.pathname + 'websocket/tracker';
            var authToken = AuthServerProvider.getToken();
            if(authToken){
                url += '?access_token=' + authToken;
            }
            var socket = new SockJS(url);
            stompClient = Stomp.over(socket);
            var stateChangeStart;
            var headers = {};
            stompClient.connect(headers, function() {
                connected.resolve('success');
                update();
            });
        }

        function disconnect () {
            if (stompClient !== null) {
                stompClient.disconnect();
                stompClient = null;
            }
        }

        function receive () {
            return listener.promise;
        }

        function update() {
        	console.log('Asked to update');
        	console.log('stompClient: ', stompClient);
            if (stompClient !== null && stompClient.connected) {
            	console.log('Actually updating');
                stompClient
                    .send('/sendgamelist',
                    {},
                    'Hello');
            }
        }

        function subscribe () {
            connected.promise.then(function() {
                subscriber = stompClient.subscribe('/topic/gamelistener', function(data) {
                    listener.notify(angular.fromJson(data.body));
                });
            }, null, null);
        }

        function unsubscribe () {
            if (subscriber !== null) {
                subscriber.unsubscribe();
            }
            listener = $q.defer();
        }
    }
})();
