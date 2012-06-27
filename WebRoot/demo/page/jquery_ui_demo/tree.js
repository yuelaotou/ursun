

// --- Implement Cut/Copy/Paste --------------------------------------------
var clipboardNode = null;
var pasteMode = null;

function copyPaste(action, dtnode) {
	switch (action) {
		case "cut" :
		case "copy" :
			clipboardNode = dtnode;
			pasteMode = action;
			break;
		case "paste" :
			if (!clipboardNode) {
				alert("Clipoard is empty.");
				break;
			}
			if (pasteMode == "cut") {
				// Cut mode: check for recursion and remove source
				var isRecursive = false;
				var cb = clipboardNode.toDict(true, function(dict) {
					// If one of the source nodes is the target, we must not
					// move
						if (dict.key == dtnode.data.key)
							isRecursive = true;
					});
				if (isRecursive) {
					alert("Cannot move a node to a sub node.");
					return;
				}
				dtnode.addChild(cb);
				clipboardNode.remove();
			} else {
				// Copy mode: prevent duplicate keys:
				var cb = clipboardNode.toDict(true, function(dict) {
					dict.title = "Copy of " + dict.title;
					delete dict.key; // Remove key, so a new one will be
										// created
					});
				dtnode.addChild(cb);
			}
			clipboardNode = pasteMode = null;
			// Must enable context menu for new nodes
			bindContextMenu("#ajax-tree");
			break;
		default :
			alert("Unhandled clipboard action '" + action + "'");
	}
};

// --- Contextmenu helper --------------------------------------------------

function bindContextMenu(objId) {
	// Add context menu to document nodes:
	$(objId + "  .ui-dynatree-document," + objId + " .ui-dynatree-folder")
			.destroyContextMenu() // unbind
			// first,
			// to
			// prevent
			// duplicates
			.contextMenu(
					{
						menu : "myMenu"
					},
					function(action, el, pos) {
						var dtnode = el.attr("dtnode");
						switch (action) {
							case "cut" :
							case "copy" :
							case "paste" :
								copyPaste(action, dtnode);
								break;
							default :
								alert("Todo: appply action '" + action
										+ "' to node " + dtnode);
						}
					});

};

// --- Init dynatree during startup ----------------------------------------

$(function() {

	$("#html-tree").dynatree( {
		persist : true,
		onActivate : function(dtnode) {
			$("#echoActivated").text(dtnode.data.title + ", key="
					+ dtnode.data.key);
		},
		onClick : function(dtnode, event) {
			// Eat keyboard events, while a menu is open
			if ($(".contextMenu:visible").length > 0)
				return false;
		},
		onKeydown : function(dtnode, event) {
			// Eat keyboard events, when a menu is open
			if ($(".contextMenu:visible").length > 0)
				return false;

			switch (event.which) {

				// Open context menu on [Space] key (simulate right click)
				case 32 : // [Space]
					$(dtnode.span).trigger("mousedown", {
						preventDefault : true,
						button : 2
					}).trigger("mouseup", {
						preventDefault : true,
						pageX : dtnode.span.offsetLeft,
						pageY : dtnode.span.offsetTop,
						button : 2
					});
					return false;

					// Handle Ctrl-C, -X and -V
				case 67 :
					if (event.ctrlKey) { // Ctrl-C
						copyPaste("copy", dtnode);
						return false;
					}
					break;
				case 86 :
					if (event.ctrlKey) { // Ctrl-V
						copyPaste("paste", dtnode);
						return false;
					}
					break;
				case 88 :
					if (event.ctrlKey) { // Ctrl-X
						copyPaste("cut", dtnode);
						return false;
					}
					break;
			}
		}
	});
	$("#ajax-tree").dynatree( {
		persist : false,
		imagePath: "images/", 
		initAjax : {
			root:"root",
			url : "tree.json"
	},
	selectMode : 3,
	onActivate : function(dtnode) {
		$("#echoActivated")
				.text(dtnode.data.title + ", key=" + dtnode.data.key);
	},
	onClick : function(dtnode, event) {
		// Eat keyboard events, while a menu is open
		if ($(".contextMenu:visible").length > 0)
			return false;
	},
	onKeydown : function(dtnode, event) {
		// Eat keyboard events, when a menu is open
		if ($(".contextMenu:visible").length > 0)
			return false;

		switch (event.which) {

			// Open context menu on [Space] key (simulate right click)
			case 32 : // [Space]
				$(dtnode.span).trigger("mousedown", {
					preventDefault : true,
					button : 2
				}).trigger("mouseup", {
					preventDefault : true,
					pageX : dtnode.span.offsetLeft,
					pageY : dtnode.span.offsetTop,
					button : 2
				});
				return false;

				// Handle Ctrl-C, -X and -V
			case 67 :
				if (event.ctrlKey) { // Ctrl-C
					copyPaste("copy", dtnode);
					return false;
				}
				break;
			case 86 :
				if (event.ctrlKey) { // Ctrl-V
					copyPaste("paste", dtnode);
					return false;
				}
				break;
			case 88 :
				if (event.ctrlKey) { // Ctrl-X
					copyPaste("cut", dtnode);
					return false;
				}
				break;
		}
	},
	checkbox : true,
	onPostInit : function(dtnode, event) {
		bindContextMenu("#ajax-tree");
	}
})	;
});
