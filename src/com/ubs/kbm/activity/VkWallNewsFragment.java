package com.ubs.kbm.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.perm.kate.api.WallMessage;
import com.ubs.kbm.R;
import com.ubs.kbm.adapters.NewsListAdapter;
import com.ubs.kbm.dao.NewsDao;
import com.ubs.kbm.domain.NewsDataPost;
import com.ubs.kbm.utils.GroupNewsService;
import com.ubs.kbm.utils.vk.Account;

public class VkWallNewsFragment extends Fragment {
	private GridView newsList;
	private Account account = new Account();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = null;
		rootView = inflater
				.inflate(R.layout.news_list_layout, container, false);
		account.restore(getActivity());
		String accessToken = account.getAccessToken();
		if ((accessToken == null) || accessToken.equals("")) {
			startActivity(new Intent(getActivity(), LoginActivity.class));
		}
		newsList = (GridView) rootView.findViewById(R.id.news_list);
		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		new DownloadWallMassegesTask().execute();
	}

	class DownloadWallMassegesTask extends AsyncTask<Void, Void, Void> {
		ArrayList<WallMessage> list;

		private List<NewsDataPost> listOfData;
		private NewsDao dao;

		public DownloadWallMassegesTask() {
			dao = new NewsDao(getActivity());
		}

		@Override
		protected Void doInBackground(Void... params) {
			listOfData = dao.getAll();
			if (listOfData != null) {
				if (listOfData.size() == 0) {
					// This is KOSTYL!!! Remake!
					getActivity().startService(
							new Intent(getActivity(), GroupNewsService.class)
									.putExtra("accessToken",
											new Account().getAccessToken()));
				}
			} else {
				listOfData = new ArrayList<NewsDataPost>();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			NewsListAdapter adapter = new NewsListAdapter(getActivity(),
					listOfData);
			newsList.setAdapter(adapter);
			adapter.notifyDataSetChanged();

		}

	}

}
