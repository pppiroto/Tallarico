package info.typea.tallarico.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * <h1>作成</h1>
 * <ol> 
 * <li>@Entity付与 でエンティティであることを認識させる</li>
 * <li>@Id付与 で主キー設定</li>
 * <li>@GeneratedValue で識別子の値を自動生成させる</li>
 * <li>@Column でデフォルトのカラムマッピングをカスタマイズ</li>
 * </ol>
 * 
 * <h1>JPQL</h1>
 * <ol>
 * <li>JPAでは、SQLのかわりにJPQLを使用する</li>
 * <li>JPQLは動的、静的、ネイティブSQLも実行可能</li>
 * <li>静的クエリは、名前付きクエリ(Named Query)ともいい、アノテーション、XMLを使用して定義できる</li>
 * </ol>
 * 
 * <h1>Named Query</h1>
 * <li>単独なら@NamedQuery、複数の場合@NamedQueriesでまとめる</li>
 * @author piroto
 */
@Entity
@NamedQueries({
	@NamedQuery(name=Book.QUERY_FIND_BY_ID,
				query="select b from Book b where b.id = :id"),
	@NamedQuery(name=Book.QUERY_SELECT_ALL,
				query="select b from Book b"),
	@NamedQuery(name=Book.QUERY_SELECT_BY_TITLE,
				query="select b from Book b where b.title like :title")})
@NamedNativeQuery(name=Book.QUERY_SELECT_MORE_EXPENSIVE,
				query="select * from book where price > :price",
				resultClass=Book.class)
public class Book {
	public static final String QUERY_FIND_BY_ID = "Book.findBookById";
	public static final String QUERY_SELECT_ALL = "Book.selectAllBooks";
	public static final String QUERY_SELECT_BY_TITLE = "Book.selectBooksByTitle";
	public static final String QUERY_SELECT_MORE_EXPENSIVE = "Book.selectMoreExpensiveBooks";
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false)
	private String title;
	private Float price;
	@Column(length=2000)
	private String description;
	private String isbn;
	private Integer nbOfPage;
	private Boolean illustrations;

	public Book(){}
	
	public Book(
			String title, 
			Float price, 
			String description,
			String isbn,
			Integer nbOfPage,
			Boolean illustrations){
		this.title = title;
		this.price = price;
		this.description = description;
		this.isbn = isbn;
		this.nbOfPage = nbOfPage;
		this.illustrations = illustrations;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public Integer getNbOfPage() {
		return nbOfPage;
	}
	public void setNbOfPage(Integer nbOfPage) {
		this.nbOfPage = nbOfPage;
	}
	public Boolean getIllustrations() {
		return illustrations;
	}
	public void setIllustrations(Boolean illustrations) {
		this.illustrations = illustrations;
	}
	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", price=" + price
				+ ", description=" + description + ", isbn=" + isbn
				+ ", nbOfPage=" + nbOfPage + ", illustrations=" + illustrations
				+ "]";
	}
}
