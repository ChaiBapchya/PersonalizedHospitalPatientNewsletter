package com.messengers.templating;

import static j2html.TagCreator.a;
import static j2html.TagCreator.attrs;
import static j2html.TagCreator.body;
import static j2html.TagCreator.each;
import static j2html.TagCreator.h1;
import static j2html.TagCreator.h2;
import static j2html.TagCreator.h3;
import static j2html.TagCreator.head;
import static j2html.TagCreator.html;
import static j2html.TagCreator.img;
import static j2html.TagCreator.p;
import static j2html.TagCreator.strong;
import static j2html.TagCreator.table;
import static j2html.TagCreator.td;
import static j2html.TagCreator.title;
import static j2html.TagCreator.tr;

import j2html.Config;
import j2html.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Material;


public class EmailHtmlWriter extends EmailWriter {
  private String primaryColor = MaterialDesignColors.Green500;
  private String secondaryColor = MaterialDesignColors.Amber700;

  // border, cellpadding, cellspacing, width, align, and valign are supported in all email clients
  private static final String ATTR_BODY_TABLE = "border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\" width=\"100%\"";
  private static final String ATTR_EMAIL_CONTAINER = "border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600px\" id=\"emailContainer\"";
  private static final String ATTR_EMAIL_SECTION = "border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"";
  private static final String ATTR_TABLE_DEFAULT = "border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"";
  private static final String ATTR_CENTER_TOP_TD = "align=\"center\" valign=\"top\"";
  private static final String ATTR_RIGHT_TOP_TD = "align=\"right\" valign=\"top\"";

  private static final String STYLE_LINK = "color: #3386e4;";

  public EmailHtmlWriter() {
    Config.textEscaper = text -> text;
  }

  @Override
  public String writeHtmlEmailString() {
    String html =  html(
        head(
            title(this.getTitle())
        ),
        body(
            makeContentTable()
        )
    ).render();

    return html;
  }

  private Tag makeContentTable() {
    return table(
        tr(
            td(
                table(
                    tr(
                        td(
                            makeHeaderContent()
                        ).attr(ATTR_CENTER_TOP_TD)
                    ),
                    tr(
                        td(
                            makeBodyContent()
                        ).attr(ATTR_CENTER_TOP_TD)
                    ),
                    tr(
                        td(
                            makeFooterContent()
                        ).attr(ATTR_CENTER_TOP_TD)
                    )

                ).attr(ATTR_EMAIL_CONTAINER)
            ).attr(ATTR_CENTER_TOP_TD)
        )
    ).attr(ATTR_BODY_TABLE).withStyle("font-family: 'Trebuchet MS', Helvetica, sans-serif;");
  }


  private static final String STYLE_HEADER_SHAPE = "border: 0px solid #666; border-radius: 0px;"
      + "padding: 8px;"
      + "padding-top: 15px;"
      + "padding-bottom: 15px;"
      + "width: 100%;";
  private static final String ATTR_HEADER_TABLE = "border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600px\"";
  private static final String STYLE_SMALL_FONT = "margin: 0; font-size: 12px;";

  private Tag makeHeaderContent() {
    return table(attrs("#email_header"),
        tr(
            td(
                table(
                    tr(
                        td(
                            table(
                                tr(
                                    td(
                                        p(
                                            "Regarding: " + this.getRecipientName()
                                        ).withStyle("margin: 0; color: white;")
                                    ),
                                    td(
                                        p(
                                            this.formatEmailDate()
                                        ).withStyle("margin: 0;  color: white;")
                                    ).attr(ATTR_RIGHT_TOP_TD).withStyle("padding-left: 20px; text-align: right;")
                                )
                            ).attr(ATTR_TABLE_DEFAULT).withStyle(STYLE_SMALL_FONT)
                        )
                    ),
                    tr(
                        td(
                            h1(
                                strong(
                                    this.getTitle()
                                )
                            ).withStyle("margin-bottom: 0px; margin-top: 5px; color: white;")
                        )
                    )
                ).attr(ATTR_HEADER_TABLE)
            ).withStyle(STYLE_HEADER_SHAPE + "background-color: " + primaryColor + ";")
        )
    ).attr(ATTR_EMAIL_SECTION);
  }

  private Tag makeBodyContent() {
    return table(attrs("#email_body"),
        tr(
            td(
                makeNewsItemListHtml(this.getNewsItems())
            )
        )
    ).attr(ATTR_EMAIL_SECTION);
  }


  private static final String STYLE_FOOTER_SHAPE = "border: 0px solid #666; border-radius: 0px;"
      + "padding: 8px;"
      + "padding-top: 15px;"
      + "padding-bottom: 15px;"
      + "width: 100%;";
  private static final String ATTR_FOOTER_TABLE = "border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600px\"";

  private Tag makeFooterContent() {
    List<Tag> tags = new ArrayList<>();

    if (getEmailSenderName() != null) {
      tags.add(h3(
          getEmailSenderName()
      ).withStyle("margin: 3px; color: white;"));
    }

    if (getSenderEmail() != null) {
      tags.add(p(
          getSenderEmail()
      ).withStyle("margin: 3px; color: white;"));
    }

    if (getSenderContactInfo() != null) {
      tags.add(p(
          getSenderContactInfo()
      ).withStyle("margin: 3px; color: white;"));
    }

    Tag[] arr = new Tag[tags.size()];
    arr = tags.toArray(arr);

    return table(attrs("#email_header"),
        tr(
            td(
                table(
                    tr(
                        td(
                            arr
                        ).attr("valign=\"middle\" align=\"middle\"")
                    )
                ).attr(ATTR_FOOTER_TABLE)
            ).withStyle(STYLE_FOOTER_SHAPE + "background-color: " + primaryColor + ";")
        )
    ).attr(ATTR_EMAIL_SECTION);
  }


  private static final String STYLE_BORDER_TOP_TR_CLASS = "border-top-width: 1px; border-top-style: solid; border-top-color: #666;";
  private static final String STYLE_BORDER_BOTTOM_TR_CLASS = "border-bottom-width: 1px;"
      + "border-bottom-style: solid;"
      + "border-bottom-color: #999;"
      + "padding: 10px";

  private Tag makeNewsItemListHtml(List<NewsItem> newsItems) {
    return table(
        each(newsItems, newsItem ->
            tr(
                td(
                    newsItemHtml(newsItem)
                ).withStyle(STYLE_BORDER_BOTTOM_TR_CLASS)
            )
        )
    );
  }

  private static final String STYLE_ITEM_DATE = "margin: 0; font-size: 12px;";
  private static final String STYLE_ITEM_HEADER = "margin: 0px;";

  private Tag newsItemHtml(NewsItem newsItem) {
    List<Tag> tags = new ArrayList<>();

    // Assuming that there is indeed a title for this NewsItem
    tags.add(h2(newsItem.getTitle()).withStyle(STYLE_ITEM_HEADER + "color:" + secondaryColor + ";"));

    if (newsItem.getDate() != null) {
      tags.add(p(this.formatNewsItemDate(newsItem.getDate())).withStyle(STYLE_ITEM_DATE));
    }

    if (newsItem.getImageUrl() != null) {
      tags.add(img()
          .withSrc(newsItem.getImageUrl())
          .withStyle("max-width: 150px; max-height: 250px; float: right; margin: 5px"));
    }

    // Assuming that there is indeed a main info for this NewsItem
    tags.add(p(newsItem.getMainInfo()));

    if (newsItem.getLearnMoreLink() != null) {
      tags.add(a(strong("Learn More"))
          .withHref(newsItem.getLearnMoreLink())
          .withStyle(STYLE_LINK)
      );
    }

    Tag[] arr = new Tag[tags.size()];
    arr = tags.toArray(arr);

    return table(
        tr(
            td(
                arr
            )
        )
    );
  }


  public String getPrimaryColor() {
    return primaryColor;
  }

  public void setPrimaryColor(String primaryColor) {
    this.primaryColor = primaryColor;
  }

  public String getSecondaryColor() {
    return secondaryColor;
  }

  public void setSecondaryColor(String secondaryColor) {
    this.secondaryColor = secondaryColor;
  }
}



