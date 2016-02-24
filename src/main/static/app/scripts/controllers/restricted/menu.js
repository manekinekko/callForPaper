'use strict';

angular.module('CallForPaper').controller('UserMenuCtrl', function(isProfileComplete, currentUser) {

    this.isProfileComplete = isProfileComplete;
    this.currentUser = currentUser;

});
