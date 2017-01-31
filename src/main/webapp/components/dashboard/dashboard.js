/**
* steel.dashboard Module
* Description
*/
angular.module('steel.dashboard', ['steel.authentication'])

.component('dashboard',{

	templateUrl : './components/dashboard/dashboard-view.html',
	controller : ['SessionService',function DashBoardController(sessionService){
		this.username = sessionService.currentUser().username;
	}]

});