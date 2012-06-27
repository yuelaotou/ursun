package cn.ursun.console.app.domain;

/**
 * <p>用户扩展字段描述</p>
 * @author 宋成山 - songchengshan@neusoft.com
 * @version 1.0
 */
public class UserExtendColumn {

	/**
	 * 排序索引
	 */
	private int index = -1;

	/**
	 * 字段名
	 */
	private String name = null;

	/**
	 * 字段最小长度
	 */
	private String minlength = null;

	/**
	 * 字段最大长度
	 */
	private String maxlength = null;

	/**
	 * 字段类型（dict,text,date,number,textarea）
	 */
	private String type = null;

	/**
	 * 字段标题
	 */
	private String label = null;

	/**
	 * 字典表对应表名
	 */
	private String tableName = null;

	/**
	 * 字典表对应代码名称字段
	 */
	private String codeName = null;

	/**
	 * 字典表对应代码值字段
	 */
	private String codeValue = null;

	/**
	 * 必填项(false|true)
	 */
	private String require = null;

	/**
	 * 格式
	 */
	private String format = null;

	/**
	 * 关联的字段ID
	 */
	private String relateColumn = null;

	/**
	 * 过滤字段名
	 */
	private String filterName = null;

	/**
	 * 过滤字段值
	 */
	private String filterValue = null;

	/**
	 * 下拉列表是否多选(false|true)
	 */
	private String multiple = null;

	/**
	 * 是否作为列表查询条件(false|true)
	 */
	private String isquery = null;
	
	/**
	 * 查询方式(like,=,>,<)
	 */
	private String operate = null;

	public String getRequire() {
		return require;
	}

	public void setRequire(String require) {
		this.require = require;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String lable) {
		this.label = lable;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public String getFilterValue() {
		return filterValue;
	}

	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}

	public String getRelateColumn() {
		return relateColumn;
	}

	public void setRelateColumn(String relateColumn) {
		this.relateColumn = relateColumn;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getMinlength() {
		return minlength;
	}

	public void setMinlength(String minlength) {
		this.minlength = minlength;
	}

	public String getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}

	
	public String getIsquery() {
		return isquery;
	}

	
	public void setIsquery(String isquery) {
		this.isquery = isquery;
	}

	
	public String getOperate() {
		return operate;
	}

	
	public void setOperate(String operate) {
		this.operate = operate;
	}

}
