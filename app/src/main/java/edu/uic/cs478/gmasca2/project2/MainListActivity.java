package edu.uic.cs478.gmasca2.project2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainListActivity extends Activity {

    public ListView listViewObject;
    public SongListAdapter adapter;
    public List<SongItem> songList;

    public String m_SongName;
    public String m_SongArtist;
    public String m_SongWiki;
    public String m_ArtistWiki;
    public String m_SongVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewObject = findViewById(R.id.listView);

        songList = new ArrayList<>();

        songList.add(new SongItem("Attention","Charlie Puth", "https://en.wikipedia.org/wiki/Attention_(Charlie_Puth_song)", "https://en.wikipedia.org/wiki/Charlie_Puth", "https://www.youtube.com/watch?v=nfs8NYg7yQM"));
        songList.add(new SongItem("Havana","Camila Cabello", "https://en.wikipedia.org/wiki/Havana_(Camila_Cabello_song)", "https://en.wikipedia.org/wiki/Camila_Cabello", "https://www.youtube.com/watch?v=HCjNJDNzw8Y"));
        songList.add(new SongItem("Thunder","Imagine Dragons", "https://en.wikipedia.org/wiki/Thunder_(Imagine_Dragons_song)", "https://en.wikipedia.org/wiki/Imagine_Dragons", "https://www.youtube.com/watch?v=fKopy74weus"));
        songList.add(new SongItem("What About Us","Pink", "https://en.wikipedia.org/wiki/What_About_Us_(Pink_song)", "https://en.wikipedia.org/wiki/Pink_(singer)", "https://www.youtube.com/watch?v=ClU3fctbGls"));
        songList.add(new SongItem("Blank Space","Taylor Swift", "https://en.wikipedia.org/wiki/Blank_Space", "https://en.wikipedia.org/wiki/Taylor_Swift", "https://www.youtube.com/watch?v=e-ORhEE9VVg"));

        adapter = new SongListAdapter(this, songList);

        listViewObject.setAdapter(adapter);

        final Intent intent = new Intent(this, WebActivity.class);


        listViewObject.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                SongItem song = (SongItem) adapterView.getItemAtPosition(i);
//                String y = song.getSongName();
                String sv = song.getSongVideo();

//                Toast.makeText(MainListActivity.this,Long.toString(l), Toast.LENGTH_LONG).show();

                intent.putExtra("song_video",sv);
                startActivity(intent);
            }
        });

        registerForContextMenu(listViewObject);

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_options_menu, menu);
        menu.addSubMenu(Menu.NONE,20, 1, "Remove Song");
        return true;
    }



    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.add_song:
                addSongOptionSelected();
                return true;
            case 20:
//                Toast.makeText(MainListActivity.this,"Got it", Toast.LENGTH_LONG).show();
                removeSongOptionSelected(item);
                return true;
            case R.id.exit_app:
                finish();
                return true;
            default:
                if(songList.size()==1)
                    Toast.makeText(MainListActivity.this,"Minimum should be 1", Toast.LENGTH_LONG).show();
                else
                {
                    for(int j=0;j<songList.size();j++)
                    {
                        if(item.getTitle()==songList.get(j).getSongName())
                        {
                            songList.remove(j);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
                return super.onOptionsItemSelected(item);
        }

        //respond to menu item selection
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        MenuItem remove = menu.getItem(1);
        return super.onPrepareOptionsMenu(menu);
    }

    public void addSongOptionSelected()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainListActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.add_song_layout,null);

        final EditText addSongName = mView.findViewById(R.id.add_song_title);

        final EditText addSongArtist = mView.findViewById(R.id.add_song_artist);

        final EditText addSongWiki = mView.findViewById(R.id.add_song_wiki);

        final EditText addArtistWiki = mView.findViewById(R.id.add_artist_wiki);

        final EditText addSongVideo = mView.findViewById(R.id.add_song_video);

        dialog.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_SongName = addSongName.getText().toString();
                m_SongArtist = addSongArtist.getText().toString();
                m_SongWiki = addSongWiki.getText().toString();
                m_ArtistWiki = addArtistWiki.getText().toString();
                m_SongVideo = addSongVideo.getText().toString();

                songList.add(new SongItem(m_SongName, m_SongArtist, m_SongWiki, m_ArtistWiki, m_SongVideo));
                adapter.notifyDataSetChanged();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.setView(mView);
        AlertDialog ad = dialog.create();
        ad.show();
    }

    public void removeSongOptionSelected(MenuItem item)
    {
        SubMenu removesong = item.getSubMenu();
        for(int i=0;i<songList.size();i++)
            removesong.add(Menu.NONE,i+100,Menu.NONE,songList.get(i).getSongName());
        invalidateOptionsMenu();
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, 1, Menu.NONE, "View Video");
        menu.add(Menu.NONE, 2, Menu.NONE, "View Song Wiki");
        menu.add(Menu.NONE, 3, Menu.NONE, "View Artist Wiki");
    }

    // This is executed when the user selects an option
//    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        switch (item.getItemId()) {
            case 1:
//                int x = info.position;
//                Toast.makeText(MainListActivity.this,Integer.toString(x), Toast.LENGTH_LONG).show();
                String video = songList.get(info.position).getSongVideo();
                Intent intent1 = new Intent(this, WebActivity.class);
                intent1.putExtra("song_video",video);
                startActivity(intent1);
                return true;
            case 2:
                String wikisong = songList.get(info.position).getSongWiki();
                Intent intent2 = new Intent(this, WebActivity.class);
                intent2.putExtra("song_video",wikisong);
                startActivity(intent2);
                return true;
            case 3:
                String wikiartist = songList.get(info.position).getArtistWiki();
                Intent intent3 = new Intent(this, WebActivity.class);
                intent3.putExtra("song_video",wikiartist);
                startActivity(intent3);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}
