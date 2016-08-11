//script to prevent page to be framed in legacy browsers (that do no support X-Frame-Options)
if (self !== top) {
	top.location = self.location;
}
