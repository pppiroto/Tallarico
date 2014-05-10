package info.typea.tallarico.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * <ol> 
 * <li>@Entity付与 でエンティティであることを認識させる</li>
 * <li>@Id付与 で主キー設定</li>
 * <li>@GeneratedValue で識別子の値を自動生成させる</li>
 * <li>@Column でデフォルトのカラムマッピングをカスタマイズ</li>
 * </ol>
 * @author piroto
 */
@Entity
public class Book {

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
}
