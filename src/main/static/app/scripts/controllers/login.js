'use strict';

angular.module('CallForPaper').controller('LoginCtrl', function($scope, $auth, tracks, talkformats) {
    $scope.loading = false;
    $scope.alreadyLinked = false;
    $scope.tracks = tracks;
    $scope.talkFormats = talkformats;


    $scope.authenticate = function(provider) {
        $scope.loading = true;
        $auth.authenticate(provider).then(function() {
            // Signed In.
            $scope.$emit('authenticate');
            $scope.loading = false;
        }).catch(function(response) {
            // Not signed In.
            $scope.loading = false;
            if (response.status === 409) {
                $scope.alreadyLinked = true;
            }
        });
    };

    $scope.badCredentials = false;
    $scope.login = function() {
        $scope.loading = true;
        $auth.login({
                email: $scope.email,
                password: $scope.password
            })
            .then(function() {
                $scope.loading = false;
                $scope.$emit('authenticate');
            })
            .catch(function(response) {
                $scope.loading = false;
                if (response.status === 401) {
                    $scope.badCredentials = true;
                }
            });
    };
});
