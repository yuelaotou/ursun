(function($) {
	// 初始化分页信息
	$.fn.initPage = function(data) {
		var pageConfig = this.data("pageConfig");
		var intStart = parseInt(findValue(pageConfig + ".start", data));
		var intPageSize = parseInt(findValue(pageConfig + ".limit", data));
		var intTotalRecords = parseInt(findValue(pageConfig + ".totalCount",
				data));
		var totalPage = Math.ceil(intTotalRecords / intPageSize);
		var currPage = parseInt(intStart / intPageSize) + 1;
		this.find("#" + pageConfig + "\\.start").val(intStart);
		this.find("#" + pageConfig + "\\.limit").val(intPageSize);
		this.find("#" + pageConfig + "\\.totalCount").val(intTotalRecords);

		this.find(".totalPage").text(totalPage);
		this.find(".totalRecords").text(intTotalRecords);

		this.find("#currPage").val(currPage);
		this.find("#totalPage").val(totalPage);

		if (intTotalRecords <= 0)
			this.find(".grid_empty_tr").show();
		else
			this.find(".grid_empty_tr").hide();

		var first = this.find(".page_first");
		var previous = this.find(".page_previous");
		var next = this.find(".page_next");
		var last = this.find(".page_last");

		if (currPage > 1 && totalPage > 0) {
			first.addClass("page_first_enable");
			first.removeClass("page_first_disable");
		} else {
			first.addClass("page_first_disable");
			first.removeClass("page_first_enable");
		}

		if (currPage > 1) {
			previous.removeClass("page_previous_disable");
			previous.addClass("page_previous_enable");
		} else {
			previous.removeClass("page_previous_enable");
			previous.addClass("page_previous_disable");
		}

		if (totalPage >= currPage + 1) {
			next.addClass("page_next_enable");
			next.removeClass("page_next_disable");
		} else {
			next.addClass("page_next_disable");
			next.removeClass("page_next_enable");
		}

		if (currPage < totalPage) {
			last.addClass("page_last_enable");
			last.removeClass("page_last_disable");
		} else {
			last.addClass("page_last_disable");
			last.removeClass("page_last_enable");
		}

		function findValue(key, data) {
			var value = data;
			$.each(key.split("."), function(i, s) {
						value = value[s];
					});
			return value;
		}
	}

	$.fn.initGrid = function(options) {
		this.data("columnModel", options.columeModel);
		this.data("recNum", options.recNum);
		this.data("pageConfig", options.pageConfig);
		this.data("isSingle", options.isSingle);
		this.data("groupConfig", options.groupConfig);
		this.data("dataSource", options.dataSource);
		this.data("theme", options.theme);
		this.data("onclick", options.onclick);
		var count = this.find("#" + options.pageConfig + "\\.totalCount").val();
		if (count <= 0)
			this.find(".grid_empty_tr").show();
		else
			this.find(".grid_empty_tr").hide();

		this.loadGrid();
	}
	$.fn.loadGrid = function() {
		var columeModel = this.data("columnModel");
		var recNum = this.data("recNum");
		var gridObject = this;
		// 初始化自定义列
		$.each(columeModel, function(i, config) {
					if (config.type == "CUSTOM") {
						for (var j = 0; j < recNum; j++) {
							var cell = gridObject.find("span[rowIndex='" + j
									+ "'][colIndex='" + config.colIndex + "']");
							var customFun = eval(config.renderTo);
							var rowData = gridObject.getRowDataByIndex(j);

							cell.after(customFun(j, rowData[config.dataField],
									rowData));
						}
					}
				});
	}
	$.fn.getColDataByIndex = function(index) {
		var data = [];
		this.find("span[dataField][colIndex='" + index + "'][rowIndex!=-1]")
				.each(function(i, span) {
					data[data.length] = ($(span).find(":has(p)").length > 0)
							? $(span + " a").text()
							: $(span).text();
				});
		return data;
	}
	$.fn.getRowDataByIndex = function(rIndex) {
		var rowData = {};
		this.find("span[dataField][rowIndex='" + rIndex + "']").each(
				function(index, span) {
					rowData[$(span).attr("dataField")] = ($(span)
							.find(":has(a)").length > 0) ? $(span + " p")
							.text() : $(span).text();
				})
		return rowData;
	}
	$.fn.gotoPageGrid = function(operation, page) {
		var pageConfig = this.data("pageConfig");
		var intStart = parseInt(this.find("#" + pageConfig + "\\.start").val());
		var intPageSize = parseInt(this.find("#" + pageConfig + "\\.limit")
				.val());
		var intTotalRecords = parseInt(this.find("#" + pageConfig
				+ "\\.totalCount").val());
		var intCurrPage = parseInt(this.find("#currPage").val());
		var intTotalPage = parseInt(this.find("#totalPage").val());

		if (operation == "first") {
			if (intCurrPage == 1) {
				return;
			}
			intStart = 0;
		} else if (operation == "prev") {
			if (intStart - intPageSize < 0) {
				return;
			}
			intStart = intStart - intPageSize;
		} else if (operation == "next") {
			if (intStart + intPageSize >= intTotalRecords) {
				return;
			}
			intStart = intStart + intPageSize;
		} else if (operation == "last") {
			if (intCurrPage >= intTotalPage) {
				return;
			}
			intStart = (intTotalPage - 1) * intPageSize;
		} else if (operation == "gotoPage") {
			if (!page)
				page = this.find("#currPage").val();
			if (page > intTotalPage)
				page = intTotalPage;
			if (page <= 0)
				page = 1;
			this.find("#currPage").val(page);
			intStart = (page - 1) * intPageSize;
		} else {
			alert("operation error!");
		}
		this.find("#" + pageConfig + "\\.start").val(intStart);
		this.reloadGrid();
	};

	$.fn.doSortGrid = function(srcObj, indx, sortField) {
		var pageConfig = this.data("pageConfig");
		var sortMode = this.find("#" + pageConfig + "\\.sortMode").val();
		var curSortColumnIndex = this.find("#sortColumnIndex").val();
		sortMode = (curSortColumnIndex && curSortColumnIndex == indx && sortMode == "ASC")
				? "DESC"
				: "ASC";
		var sortIcon = $(srcObj).find(".grid-hd-icon");
		this.find(".grid-hd-asc").removeClass("grid-hd-asc");
		this.find(".grid-hd-desc").removeClass("grid-hd-desc");
		sortIcon.addClass(sortMode == "DESC" ? "grid-hd-desc" : "grid-hd-asc");
		this.data("sortMode", sortMode);
		this.find("#sortColumnIndex").val(indx);
		this.find("#" + pageConfig + "\\.sortColumn").val(sortField);
		this.find("#" + pageConfig + "\\.sortMode").val(sortMode);
		this.reloadGrid();
	}

	$.fn.reloadGrid = function(queryParam, call, force) {
		var gridObject = this;
		var groupConfig = gridObject.data("groupConfig");
		var pageConfig = this.data("pageConfig");
		gridObject.find("#selectAll").attr("checked", false);
		if (queryParam) {
			var paramMap = $.map(queryParam.split('&'), function(o) {
						if ($.trim(o) != "") {
							var param = o.split('=');
							if (param.length > 1) {
								return {key:decodeURIComponent(param[0]),value:decodeURIComponent(param[1])};
							} else
								return null;

						} else
							return null;
					});

			$.each(paramMap, function(i, o) {
				var hi = gridObject.find("form input:hidden[id='" + o.key + "'],form input:hidden[name='" + o.key + "']");
				if (hi.length > 0) {
					hi.val(o.value);
				} else if (force && $.trim(o.value) != "") {
					gridObject.find("form").append("<input id='" + o.key
							+ "'name='" + o.key + "'type='hidden' value='"
							+ o.value + "'>");
				}
			});

			// queryParam = queryParam.replace("+", "%2B");
			// gridObject.data("queryParam", queryParam);
			gridObject.find("#" + pageConfig + "\\.start").val(0);
			gridObject.find("#" + pageConfig + "\\.sortColumn").val("");
			gridObject.find("#" + pageConfig + "\\.sortMode").val("");
		}
		// else
		// queryParam = gridObject.data("queryParam");

		if (groupConfig) {
			gridObject.find("#" + pageConfig + "\\.groupField")
					.val(groupConfig.dataField);
			gridObject.find("#" + pageConfig + "\\.groupSort")
					.val(groupConfig.sortMode);
		}
		var url = $("form", this.get()).attr("action");
		var theme = this.data("theme");
		if (!theme || theme == "jsp" || theme == "JSP") {
			gridObject.find("form").submit();
			return;
		}
		if (call) {
			gridObject.data("call", call);
		} else {
			call = gridObject.data("call");
		}
		$.ajax({
					type : "POST",
					url : url,
					data : force?queryParam:$("form", this.get()).serialize(),
					// data : queryParam,
					dataType : "json",
					success : function(data) {
						refreshGrid(gridObject, data);
						if (call) {
							call(data);
						}
					}
				});

		function refreshGrid(gridObject, data) {
			var trTemplate = gridObject.find(".tr_template").clone(true);
			var groupTemplate = gridObject.find(".grid_group_template")
					.clone(true);
			var tbody = gridObject.find(".grid_tbody");
			var columeModel = gridObject.data("columnModel");
			var isSingle = gridObject.data("isSingle");
			var dataSource = gridObject.data("dataSource");
			var onclick = gridObject.data("onclick");
			var curGroup;
			var groupId = 0;
			// 初始化分页信息
			gridObject.initPage(data);
			// 重画列表
			var listData = data[dataSource];
			var emptyTr = gridObject.find(".grid_empty_tr").clone(true);
			tbody.empty();
			$.each(listData, function(rIndex, rowData) {
				var tr = trTemplate.clone(true);
				if (onclick && onclick != "") {
					tr.click(function() {
								eval(onclick + "(" + rIndex + ")");
							});
				}

				if (groupConfig) {
					var groupFieldValue = eval("rowData."
							+ groupConfig.dataField);
					if (curGroup != groupFieldValue
							|| (groupFieldValue === null && curGroup !== null)) {
						var groupTr = groupTemplate.clone(true);
						curGroup = rowData[groupConfig.dataField];
						drawGroup(rowData, groupTr);
						groupTr.removeClass("grid_group_template");
						groupTr.show();
						tbody.append(groupTr);
					}
				}
				tr.addClass("grid_group_tr_" + groupId);
				if ((rIndex + 1) % 2 != 0) {
					tr.addClass("gridRowEven");
				} else {
					tr.addClass("gridRowOdd");
				}
				tr.find("span[dataField]").each(function(cIndex) {
					if (columeModel[cIndex].type == "ROWNUM")
						$(this).html(rIndex + 1);
					else if (columeModel[cIndex].type == "SELECT")
						drawSelect(columeModel[cIndex], rowData, $(this),
								rIndex);
					else if (columeModel[cIndex].type == "ANCHOR")
						drawAnchor(columeModel[cIndex], rowData, $(this),
								rIndex);
					else if (columeModel[cIndex].type == "CUSTOM")
						drawCustom(columeModel[cIndex], rowData,
								$(this, cIndex), rIndex, cIndex);
					else if (columeModel[cIndex].type == "IMAGE")
						drawImage(columeModel[cIndex], rowData, $(this), rIndex);
					else if (columeModel[cIndex].type == "TEXT")
						drawText(columeModel[cIndex], rowData, $(this), rIndex);
					else
						$(this).html(rowData[columeModel[cIndex].dataField]);
					$(this).attr("rowIndex", rIndex);
				});
				tr.removeClass("tr_template");
				tr.show();
				tbody.append(tr);
			})
			tbody.append(groupTemplate);
			tbody.append(trTemplate);
			tbody.append(emptyTr);
			function drawSelect(colConfig, rowData, obj, rIndex) {
				var value = eval("rowData." + colConfig.dataField);
				value = (value == null) ? "" : value;
				value = value.replace(/"/g, '\\\"').replace(/\</g, '&lt;')
						.replace(/\>/g, '&gt;');
				if (isSingle == "true") {
					obj.before("<input type='radio' name='rowid' value="
							+ value + "'>");
				} else {
					var checkBoxStr = "<input type='checkbox' name='rowid' value="
							+ value;
					if (groupConfig) {
						checkBoxStr += " class='grid_group_chx_" + groupId
								+ "' ";
					}
					checkBoxStr += ">";
					obj.before(checkBoxStr);
				}
				obj.text(eval("rowData." + colConfig.dataField));
			}
			function drawAnchor(colConfig, rowData, obj, rIndex) {
				var parent = obj.parent();
				var anchor = $("<a href="
						+ resolveFields(colConfig.linkUrl, rowData) + "  ></a>")
						.appendTo(parent);
				obj.text(eval("rowData." + colConfig.dataField));
				obj.appendTo(anchor);
			}

			function drawCustom(colConfig, rowData, obj, rIndex, cIndex) {
				var customFun = eval(colConfig.renderTo);
				var value = customFun(cIndex, eval("rowData."
								+ colConfig.dataField), rowData);
				obj.text(eval("rowData." + colConfig.dataField));
				obj.after(value);
			}

			function drawImage(colConfig, rowData, obj, rIndex) {
				var parent = obj.parent();
				var anchor = $("<a href="
						+ resolveFields(colConfig.linkUrl, rowData) + "  ></a>")
						.appendTo(parent);

				$("<img src=\"" + colConfig.imageSrc + "\" border=0></img>")
						.appendTo(anchor);
				obj.text(eval("rowData." + colConfig.dataField));
			}

			function drawText(colConfig, rowData, obj, rIndex) {
				var strVal = eval("rowData." + colConfig.dataField);
				var objectA = $("<p></p>");
				if (strVal != null && colConfig.maxLength > 0)
					if (strVal.length > colConfig.maxLength) {
						objectA.attr("title", strVal);
						strVal = strVal.substring(0, colConfig.maxLength)
								+ "...";
					}
				objectA.text(strVal);
				objectA.appendTo(obj);
			}

			function drawGroup(rowData, obj) {
				groupId++;

				obj.find(".group_expand").click(function(groupIcoClass) {
							gridObject.expandGroup(this);
						})
				obj.find(".group_expand").attr("groupId", groupId);

				obj.find(".grid_group_chx").click(function(groupIcoClass) {
							gridObject.selectGroup(this);
						})
				obj.find(".grid_group_chx").attr("groupId", groupId);

				obj.find(".grid_group_lable").html(resolveFields(
						groupConfig.lable, rowData));
			}

			function resolveFields(pstrUrl, rowDate) {
				var intPos = 0;
				var intEnd = 0;
				var strCol = null;
				var strRet = null;
				strRet = pstrUrl;
				intPos = strRet.indexOf("{");
				while (intPos >= 0) {
					intEnd = strRet.indexOf("}", intPos + 1);

					if (intEnd != -1) {
						strCol = strRet.substring(intPos + 1, intEnd);
						var value = eval("rowDate." + strCol);
						value = (value == null) ? "" : value;
						value = value.replace(/'/g, "\\\'").replace(/"/g,
								'\\\"').replace(/\</g, '&lt;').replace(/\>/g,
								'&gt;');
						strRet = strRet.substring(0, intPos) + value
								+ strRet.substring(intEnd + 1);
					}

					intPos = strRet.indexOf("{", intPos + 1);
				}
				return strRet;
			}
		}

	}

	$.fn.expandGroup = function(o) {
		$(o).toggleClass("group_expand_close_icon");
		$(o).toggleClass("group_expand_open_icon");
		this.find(".grid_group_tr_" + o.groupId).each(function() {
					$(this).toggle();
				})
	}

	$.fn.selectGroup = function(obj) {
		if (obj.checked) {
			this.find(".grid_group_chx_" + obj.groupId).each(function(i) {
						this.checked = true;
					});
		} else {
			this.find(".grid_group_chx_" + obj.groupId).each(function(i) {
						this.checked = false;
					});
		}
	}
	$.fn.selectAllRecord = function(obj) {
		if (obj.checked) {
			this.find("input[name=rowid]").each(function(i) {
						this.checked = true;
					});
		} else {
			this.find("input[name=rowid]").each(function(i) {
						this.checked = false;
					});
		}
	}
	$.fn.selectAllRecord = function(obj) {
		if (obj.checked) {
			this.find("input[name=rowid]").each(function(i) {
						this.checked = true;
					});
		} else {
			this.find("input[name=rowid]").each(function(i) {
						this.checked = false;
					});
		}
	}

	$.fn.getSelectRowIndex = function() {
		var result = {
			selectIndex : []
		};
		this.find("input[name=rowid][checked] + span ").each(function() {
			result.selectIndex[result.selectIndex.length] = $(this)
					.attr("rowIndex");
		});
		return result;
	}

	$.fn.getSelectRowData = function() {
		var gridObject = this;
		var result = {
			rowData : []
		};
		this.find("input[name=rowid][checked] + span ").each(function() {
					var selectIndex = $(this).attr("rowIndex");
					var data = gridObject.getRowDataByIndex(selectIndex);
					result.rowData[result.rowData.length] = data;
				});
		return result;
	}

	$.fn.getSelectData = function() {
		var result = {
			rowid : []
		};
		this.find("input[name=rowid][checked]").each(function() {
					result.rowid[result.rowid.length] = $(this).val();
				});
		return result;
	}

	$.fn.onClick = function(rIndex) {
		alert(rIndex);
	}

	$.fn.onActivate = function(rIndex) {
		this.onClick(rIndex);
	}

})(jQuery);
