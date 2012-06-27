/**
 * window 窗口组件，支持拖动、模态窗口 songchengshan@neusoft.com
 */
;
(function($) {
	$.windowUI = {
		version : "0.1.0"
	};
	$.windowUI.defaults = {
		title : "defaults",
		top : '28%',
		left : '35%',
		width : 400,
		heigth : 200,
		draggable : true,
		modal : false,
		buttons : null,
		close : function() {
		}
	}
	$.fn.createWindow = function(opts) {
		opts = $.extend({}, $.windowUI.defaults, opts || {});
		var _windowObj = $("<div id='x1' class='window'></div>")
				.appendTo(document.body);
		var _titleObj = $("<div class='windowTop'><div class='windowClose windowCloseButton' /><div class='windowTopContent'>"
				+ opts.title + "</div></div>").appendTo(_windowObj);
		var _windowBody = $("<div class='windowBody'></div>")
				.appendTo(_windowObj);
		var _windowContent = $("<div class='windowContent'></div>")
				.appendTo(_windowBody);

		var _windowFooter = null;
		if (opts.buttons) {
			_windowFooter = $("<div class='windowFooter'></div>")
					.appendTo(_windowBody);
			$.each(opts.buttons, function() {
						if (this.html) {
							_windowFooter.append(this.html);
						} else {
							var button=$("<button  >" + this.text + "</button>").appendTo(_windowFooter);
							if(this.callbackFun)
								button.click(this.callbackFun);
						}
					})
		}
		_windowContent.append(this);
		this.show();
		if (opts.width) {
			_windowObj.width(opts.width);
			_windowObj.find('.windowBody').css('width', opts.width + 'px');
			_windowObj.find('.windowContent').css('width',
					opts.width - 1 + 'px');
		}
		if (opts.height) {
			_windowObj.height(opts.height);
			if (opts.buttons) {
				_windowObj.find('.windowBody, .windowContent').css('height',
						opts.height - 63 + 'px');
			} else {
				_windowObj.find('.windowBody, .windowContent').css('height',
						opts.height - 33 + 'px');
			}
		}
		(opts.top && _windowObj.css("top", opts.top));

		(opts.left && _windowObj.css("left", opts.left));

		_windowObj.find('.windowClose').bind('click', function() {
					_windowObj.closeWindow();
				});
		_windowObj.data("opts", opts);
		return _windowObj;
	}

	$.fn.showWindow = function(formSource) {

		var _windowObj = this;
		var opts = _windowObj.data("opts");
		if (formSource)
			_windowObj.data("formSource", formSource);
		else
			formSource = _windowObj.data("formSource");

		var show = function() {
			var target = _windowObj;
			if (opts.modal) {
				var endPosition = _windowObj.getPosition();
				_windowObj.css("position", "static");
				target = $.blockUI({
							message : _windowObj,
							focusInput : false,
							css : {
								padding : 0,
								margin : 0,
								width : _windowObj.width(),
								height : _windowObj.height(),
								top : endPosition.y,
								left : endPosition.x,
								textAlign : '',
								color : '',
								border : '',
								backgroundColor : '',
								cursor : ''
							},
							overlayCSS : {
								opacity : 0.6,
								cursor : '',
								backgroundColor : '#ded7de'
							}
						});
			}

			if (opts.draggable) {
				_windowObj.find(".windowTop").css('cursor', 'move');
				target.draggable({
							handle : _windowObj.find(".windowTop").get(0)
						});

			}
			_windowObj.show();
		}
		if (opts["z-index"])
			_windowObj.css("z-index", opts["z-index"]);
		if (formSource) {
			if (this.css('display') == 'none') {
				$(formSource).transferTo({
							to : _windowObj.get(0),
							className : 'transferer2',
							duration : 500,
							callback : function() {
								show();
							}
						});
			}
		} else {
			if (this.css('display') == 'none')
				show();
		}
	}

	$.fn.closeWindow = function() {
		var _windowObj = this;
		var opts = _windowObj.data("opts");
		if (opts.close && (opts.close() == false))
			return;
		if (_windowObj.css('display') == 'block') {
			if (opts.modal) {
				$.unblockUI();
			}
			var formSource = _windowObj.data("formSource");
			if (formSource) {
				_windowObj.transferTo({
							to : formSource,
							className : 'transferer2',
							duration : 500,
							callback : function() {

								_windowObj.hide();
							}
						});
			} else {
				_windowObj.hide();
			}
		}

	}

	$.fn.resetWindow = function(opts) {
		var _windowObj = this;
		(opts.title && _windowObj.find(".windowTopContent").html(opts.title));
		if (opts.width) {
			_windowObj.width(opts.width);
			_windowObj.find('.windowBody').css('width', opts.width + 'px');
			_windowObj.find('.windowContent').css('width',
					opts.width - 1 + 'px');
		}
		if (opts.height) {
			_windowObj.height(opts.height);
			_windowObj.find('.windowBody, .windowContent').css('height',
					opts.height - 33 + 'px');
		}
		(opts.top && _windowObj.css("top", opts.top));

		(opts.left && _windowObj.css("left", opts.left));

		if (opts.close) {
			_windowObj.find('.windowClose').unbind('click');

			_windowObj.find('.windowClose').bind('click', function() {
						_windowObj.closeWindow();
					});
		}
		var oldOpts = _windowObj.data("opts");
		opts = $.extend({}, oldOpts, opts || {});
		_windowObj.data("opts", opts);
		return _windowObj;

	}

	$.fn.getPosition = function() {
		var x = 0;
		var y = 0;
		var e = this.get(0);
		var es = e.style;
		var restoreStyles = false;
		if (jQuery(e).css('display') == 'none') {
			var oldVisibility = es.visibility;
			var oldPosition = es.position;
			restoreStyles = true;
			es.visibility = 'hidden';
			es.display = 'block';
			es.position = 'absolute';
		}
		var el = e;
		while (el) {
			x += el.offsetLeft
					+ (el.currentStyle && !jQuery.browser.opera
							? parseInt(el.currentStyle.borderLeftWidth) || 0
							: 0);
			y += el.offsetTop
					+ (el.currentStyle && !jQuery.browser.opera
							? parseInt(el.currentStyle.borderTopWidth) || 0
							: 0);
			el = el.offsetParent;
		}
		el = e;
		while (el && el.tagName && el.tagName.toLowerCase() != 'body') {
			x -= el.scrollLeft || 0;
			y -= el.scrollTop || 0;
			el = el.parentNode;
		}
		if (restoreStyles == true) {
			es.display = 'none';
			es.position = oldPosition;
			es.visibility = oldVisibility;
		}
		return {
			x : x,
			y : y
		};
	}
	$.fn.transferTo = function(o) {
		return this.queue(function() {
			var elem = $(this), target = $(o.to), endPosition = target
					.getPosition(), animation = {
				top : endPosition.y,
				left : endPosition.x,
				height : target.innerHeight(),
				width : target.innerWidth()
			}, startPosition = elem.offset(), transfer = $('<div class="ui-effects-transfer"></div>')
					.css("z-index", target.css("z-index"))
					.appendTo(document.body).addClass(o.className).css({
								top : startPosition.top,
								left : startPosition.left,
								height : elem.innerHeight(),
								width : elem.innerWidth(),
								position : 'absolute'
							}).animate(animation, o.duration, o.easing,
							function() {
								transfer.remove();
								(o.callback && o.callback.apply(elem[0],
										arguments));
								elem.dequeue();
							});
		});
	};
})(jQuery);
/*
 * 
 * $('#windowOpen').bind( 'click', function() { if($('#window1').css('display') ==
 * 'none') { $(this).TransferTo( { to:'window1', className:'transferer2',
 * duration: 400, complete: function() { $('#window1').show(); } } ); }
 * this.blur(); return false; } ); $('.windowClose').bind( 'click', function() {
 * $('#window1').TransferTo( { to:'windowOpen', className:'transferer2',
 * duration: 400 } ).hide(); } ); $('.windowMin').bind( 'click', function() {
 * $('.windowContent').SlideToggleUp(300); $('.windowBody,
 * .windowContent').animate({height: 10}, 300);
 * $('#window1').animate({height:40},300).get(0).isMinimized = true;
 * $(this).hide(); $('.windowResize').hide(); $('.windowMax').show(); } );
 * $('.windowMax').bind( 'click', function() { var windowSize =
 * $.iUtil.getSize(document.getElementById('windowContent'));
 * $('.windowContent').SlideToggleUp(300); $('.windowBody,
 * .windowContent').animate({height: windowSize.hb + 13}, 300);
 * $('#window1').animate({height:windowSize.hb+43}, 300).get(0).isMinimized =
 * false; $(this).hide(); $('.windowMin, .windowResize').show(); } );
 * 
 */
