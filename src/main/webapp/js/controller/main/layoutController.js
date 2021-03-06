/**
 * Created by Misaka on 2017-08-16.
 */

app.controller('LayoutController', ['$scope', '$location', '$log', 'LayoutService', 'ProgressWebSocketService',
    function ($scope, $location, $log, LayoutService, ProgressWebSocketService) {
        // create web socket
        ProgressWebSocketService.connect();

        // default category
        // $location.path('/picture');
        let mainPromises = LayoutService.loadMain();
        mainPromises.then(function success(responses) {
            for(let i = 0; i < responses.length; i++) {
                if(responses[i].config.url.indexOf('/title') > -1) {
                    $scope.title = responses[i].data;

                } else if(responses[i].config.url.indexOf('/category') > -1) {
                    $scope.categories = responses[i].data;

                } else {
                    ;
                    // another conditions..
                }
            }

        }).catch(function (error) {
            $log.error('ERROR: ', error);
            throw error;
        });
    }
]);