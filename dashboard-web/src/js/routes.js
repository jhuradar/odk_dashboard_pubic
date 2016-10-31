'use strict';

/**
 * Route configuration for the RDash module.
 */
angular.module('Dashboard').config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {

        // For unmatched routes
        $urlRouterProvider.otherwise('/');

        // Application routes
        $stateProvider
            .state('index', {
                url: '/',
                templateUrl: 'templates/dashboard.html'
            })
            .state('ratios', {
                url: '/ratios',
                templateUrl: 'templates/ratios.html'
            })
            .state('callbacks', {
                url: '/callbacks',
                templateUrl: 'templates/callbacks.html'
            })
            .state('graphs', {
                url: '/graphs',
                templateUrl: 'templates/graphs.html'
            })
            .state('duplicates', {
                url: '/duplicates',
                templateUrl: 'templates/duplicates.html'
            });
    }
]);